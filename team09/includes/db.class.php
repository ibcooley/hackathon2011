<?php
if (!defined('SECURITY')){
	include_once(HOME_DIR.DIRECTORY_SEPARATOR.'includes'.DIRECTORY_SEPARATOR.'functions.php');
	error(403, __FILE__, __LINE__);
}
class DBInfo {
	var $host = 'localhost';
	var $user = 'sparc';
	var $pass = 'sparc';
	var $db   = 'sparc';
	
	public function __construct($args = NULL){
		if(isset($args) && is_array($args)){
			if(array_key_exists('host', $args) && ($args['host'] !== '')) $this->host = $args['host'];
			if(array_key_exists('user', $args) && ($args['user'] !== '')) $this->user = $args['user'];
			if(array_key_exists('pass', $args) && ($args['pass'] !== '')) $this->pass = $args['pass'];
			if(array_key_exists('db', $args)   && ($args['db'] !== ''))   $this->db   = $args['db'];
		}
	}
	public function __destruct(){
		$this->host = NULL;
		$this->user = NULL;
		$this->pass = NULL;
		$this->db   = NULL;
	}
	public function destruct($what){
		if(isset($this->{$what})) unset($this->{$what});
		else return NULL;
	}	
}
class DB {
	private $connection   = NULL;
	private $id           = 0;
	public  $count        = 0;

	public function __construct(){
		$dbInfo = new DBInfo();
		
		if(!$this->connection = mysql_connect($dbInfo->host, $dbInfo->user, $dbInfo->pass)){
			$dbInfo->destruct('pass');
			$this->error('Could not connect to MySQL Server', 1);
		}
		
		$dbInfo->destruct('pass');
		
		if($dbInfo->db !== NULL){
			if(!mysql_select_db($dbInfo->db)){
				mysql_close($this->connection);
				$this->error('Database could not be selected.', 1);
			}
		}
		
		unset($dbInfo);
		
		return $this->connection;
	}
	public function disconnect(){
		if($dbInfo !== NULL) unset($dbInfo);
		if($this->connection){
			if($this->id) mysql_free_result($this->id);
			return mysql_close($this->connection);
		}
		else return FALSE;
	}
	public function query($query = ''){
		unset($this->id);
		
		if($query != ''){
			if($this->id = mysql_query($query, $this->connection)){			
				$this->count++;
				
				return $this->id;
			}
			else $this->error('<strong>Bad SQL Query</strong>: '.htmlentities($query).'<br /><strong>'.mysql_error().'</strong>');
		}
	}
	public function fetch_array($id = -1){
		if($id != -1) $this->id = $id;
		
		return mysql_fetch_array($this->id);
	}
	public function num_rows($id = -1){
		if($id != -1) $this->id = $id;
		
		return intval(mysql_num_rows($this->id));
	}
	public function affected_rows($id = -1){
		if($id != -1) $this->id = $id;
		
		return intval(mysql_affected_rows($this->id));
	}
	private function error($error, $halt = 0){
		trigger_error($error, E_USER_ERROR);
		
		if($halt != 0) exit;
	}
}
?>