<?php

  require_once 'DB/DbInterface.php';

  class User {
    private $login;
    private $password;
    private $id;
    private $logResult;
    private $userData;

    function __construct($userLogin, $userPassword){
      $this->login=$userLogin;
      $this->password = md5($userPassword);
      $this->logResult = $this->logUser($userLogin,$this->password);
    }

    private function logUser($login,$password){
      $result = getUserDB($login,$password);
      $this->userData = $result;
      if($result != "No such user"){
        $json['success'] = 'Welcome '.$login;
      }else{
        $add = addUserDB($login, $password);
        if($add == "Added new user"){
          $json['success'] = 'Added new user '.$login;
          $this->userData = getUserDB($login,$password);
        }
        else{
          $json['error'] = "Wrong password!";
        }
      }
      return $json;
    }

    public function checkUser(){
      return $this->logResult;
    }

    public function getUserLogin(){
      return $this->login;
    }

    public function getId(){
      $user = getUserDB($this->login,$this->password);
      return $user['user_id'];
    }

  }

?>
