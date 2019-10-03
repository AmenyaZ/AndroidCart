<?php
include_once 'braintree-php-3.21.1/lib/Braintree.php';

Braintree_Configuration::environment('sandbox');
Braintree_Configuration::merchantId('');
Braintree_Configuration::publicKey('');
Braintree_Configuration::privateKey('');

$amount=$_GET['amount'];
if(isset($_POST["NONCE"])){
  $nonceFromTheClient = $_POST["NONCE"];
  $result = Braintree_Transaction::sale([
    'amount' => $amount,
    'paymentMethodNonce' => $nonceFromTheClient,
    'options' => ['submitForSettlement' => true ]
  ]);


  if ($result->success) {
      echo("success!: " . $result->transaction->id);
  } else if ($result->transaction) {
      echo "Error processing transaction: \n" . " code: " . $result->transaction->processorResponseCode . " \n " . " text: " . $result->transaction->processorResponseText;
  } else {
      echo "Validation errors: \n"; //+ $result->errors->deepAll();
  }

}else{
  echo($clientToken = Braintree_ClientToken::generate());
}

?>