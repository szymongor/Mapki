<?php

$response = "Login : ";

if(isset($_POST['login'])){
  $response = $response.$_POST['login'];
}

echo($response);

?>
