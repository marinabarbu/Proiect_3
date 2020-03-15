<?php

require "conn.php";


$email = $_POST['email'];
$password = $_POST['psw'];

/*
$email = "bmm@yahoo.com";
$password = "bmm123";
*/

$isValidEmail = filter_var($email, FILTER_VALIDATE_EMAIL);
if($conn){
if($isValidEmail === false){
echo "This email is not valid";
}else{

$sqlCheckEmail = "SELECT * FROM `users_table` WHERE `email` LIKE '$email'";
$emailQuery = mysqli_query($conn, $sqlCheckEmail);
$correct_pass_query = "SELECT `password` FROM `users_table` WHERE `email` LIKE '$email'";

if($result = $conn->query($correct_pass_query)){
while($row = $result->fetch_assoc()){
$correct_pass = $row['password'];

}
}

$rol_query = "SELECT `rol` FROM `users_table` WHERE `email` LIKE '$email'";

if($result = $conn->query($rol_query)){
while($row = $result->fetch_assoc()){
$user_rol = $row['rol'];

}
}
#echo $user_rol;
#echo $correct_pass;

if(mysqli_num_rows($emailQuery) > 0){
	

if(password_verify($password, $correct_pass)){

$sqlLogin = "SELECT * FROM `users_table` WHERE `email` LIKE '$email' AND `password` LIKE '$correct_pass'";

$loginQuery = mysqli_query($conn, $sqlLogin);

if(mysqli_num_rows($loginQuery) > 0){
if($user_rol === "client")
{
echo "Login Success";	
}
else{
echo "Login Success admin";
}

}else{
echo "Wrong password";
}
}
}else{
echo "This Email is not registered";
	
}

}

}
else{
echo "Connection Eroor";
}

?>