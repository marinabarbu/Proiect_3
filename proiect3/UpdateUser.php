<?php

require "conn.php";

if($_SERVER['REQUEST_METHOD']=='POST'){

 $id = $_POST['UserID'];
 $S_username = $_POST['UserUsername'];
 $S_email = $_POST['UserEmail'];
 $S_mobile = $_POST['UserMobile'];
 $S_gender = $_POST['UserGender'];
 
$Sql_Query = "UPDATE users_table SET mobile= '$S_mobile', gender = '$S_gender' WHERE id = $id";

 if(mysqli_query($conn,$Sql_Query))
{
 echo 'Record Updated Successfully';
}
else
{
 echo 'Something went wrong';
 }
 }
 mysqli_close($conn);
?>