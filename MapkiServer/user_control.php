<?php

  require_once 'DB/DbInterface.php';
  header('Content-Type: application/json ');

  class User {
    function _construct(){
    }

    public function does_user_exist($email, $password){
      $result = getUser($email,$password);
      if($result != "No such user"){
        $json['success'] = 'Welcome '.$email;
      }else{
        $add = addUser($email, $password);
        if($add == "Added new user"){
          $json['success'] = 'Added new user '.$email;
        }
        else{
          $json['error'] = "Wrong password!";
        }
      }
      echo(json_encode($json));
    }

  }

  $user = new User();
  if(isset($_POST['email'],$_POST['password'])){
    $email = $_POST['email'];
    $password = $_POST['password'];

    if(!empty($email) && !empty($password)){
      $encrypted_password = md5($password);
      $user->does_user_exist($email,$encrypted_password);
    }
    else{
      echo( json_encode("You must fill both fields"));
    }

  }


?>
