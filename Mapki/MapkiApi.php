<?php
  require_once 'Utils.php';
  require_once 'User.php';
  session_start();

  $method = $_SERVER['REQUEST_METHOD'];
  switch($method){
    case 'POST':
      $request = getRequestType($_SERVER['REQUEST_URI']);
      switch($request){
        case 'login':
          $json = login();
          echo(json_encode($json));
          break;
        case 'logout':
          $json = logout();
          echo(json_encode($json));
          break;


      }
      break;
  }



  function login(){
    if(isset($_SESSION['user'])){
      $response['success'] = "You are already logged, ".$_SESSION['user']->getUserLogin();
      return $response;
    }
    if(isset($_POST['login'],$_POST['password']) ){
      if(strlen($_POST['login'])<4){
        $response['error'] = "Login must have 4 or more letters";
      }
      elseif (strlen($_POST['password'])<8) {
        $response['error'] = "Pasword must have 8 or more letters";
      }
      else{
        $user = new User($_POST['login'],$_POST['password']);
        $check = $user->checkUser();
        if(isset($check['error'])){
          $response = $check;
        }
        else{
          $response = $check;
          $_SESSION['user'] = $user;
        }

      }
    }
    else{
      $response['error'] = "Set login and password";
    }
    return $response;
  }

  function logout(){
    if(isset($_SESSION['user'])){
      $response['success'] = "You are logged off, ".$_SESSION['user']->getUserLogin();
      unset($_SESSION['user']);
    }
    else{
      $response['error'] = "Nobody was logged";
    }
    return $response;
  }
?>