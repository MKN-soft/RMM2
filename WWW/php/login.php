<?php 

// include db connect class
require_once 'FunctionsDB.php';
// connecting to db
$db = new FunctionsDB();

// username and password sent from form
$myusername=$_POST['myusername'];
$mypassword=$_POST['mypassword']; 

// To protect MySQL injection (more detail about MySQL injection)
$myusername = stripslashes($myusername);
$mypassword = stripslashes($mypassword);
$myusername = mysql_real_escape_string($myusername);

if($db->getUserByUsernameAndPassword($myusername, $mypassword)) {
	// Register $myusername, $mypassword and redirect to file "login_success.php"
	session_start();
	$_SESSION['username'] = $myusername;
	
	header("location:../panel");
}
else {
	echo "Wrong Username or Password";
}