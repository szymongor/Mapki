<?php

  require_once 'User.php';
  header('Content-Type: application/json ');


  session_start();

  if(isset($_SESSION['user'])){
    echo( json_encode($_SESSION['user']));
    unset($_SESSION['user']);
  }
  else{
    $user = new User();
    if(isset($_POST['email'],$_POST['password'])){
      $email = $_POST['email'];
      $password = $_POST['password'];

      if(!empty($email) && !empty($password)){
        $encrypted_password = md5($password);
        $user->does_user_exist($email,$encrypted_password);
        $_SESSION['user'] = $user;
      }
      else{
        echo( json_encode("You must fill both fields"));
      }
    }
  }


?>
