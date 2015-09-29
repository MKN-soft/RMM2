<?php

/**
 * Class for connect with database. Using configuration file config.php .
 */
class ConnectDB {
	
	function __construct() { }
	
	function __destruct() { }
	
	public function connect() {
		require_once 'config.php';
		// connecting to mysql
        $con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD) or die(mysql_error());
        // selecting database
        mysql_select_db(DB_DATABASE) or die(mysql_error());
 
        // return database handler
        return $con;
    }
 
    public function close() {
        mysql_close();
    }
	
}