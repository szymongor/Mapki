<?php

  require_once 'DB/DbInterface.php';

  class User {
    private $login;
    private $password;
    private $id;
    private $logResult;

    function __construct($userLogin, $userPassword){
      $this->login=$userLogin;
      $this->password = md5($userPassword);
      $this->logResult = $this->logUser($userLogin,$this->password);
    }

    private function logUser($login,$password){
      $result = getUser($login,$password);
      if($result != "No such user"){
        $json['success'] = 'Welcome '.$login;
      }else{
        $add = addUser($login, $password);
        if($add == "Added new user"){
          $json['success'] = 'Added new user '.$login;
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

  }

?>
