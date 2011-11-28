<?
if (!defined('SECURITY')){
	include_once 'functions.php';
	error(403, __FILE__, __LINE__);
}
class SecureSession {
	private $timeout       = 300;
	private $secureWord    = 'SALT_';
	private $checkBrowser  = TRUE;
	private $checkIPBlocks = 0;

	public function __construct($to = 300, $sw = 'SALT_', $cb = TRUE, $cipb = 0){
		global $db;
		
		$this->timeout       = $to;
		$this->secureWord    = $sw;
		$this->checkBrowser  = $cb;
		$this->checkIPBlocks = $cipb;

		session_start();
				
		$db->query('DELETE FROM `sessions` WHERE (NOW() - '.$this->timeout.') > `time`;');
		$db->query('DELETE FROM `session_vars` WHERE (NOW() - '.$this->timeout.') > `time`;');
		
		if(!$this->isValid()){
			session_destroy();
			session_start();
			session_regenerate_id();
			
			$db->query('INSERT INTO `sessions` VALUES(\''.session_id().'\', \''.$this->fingerprint().'\', NOW());');
			$this->set('ss_fingerprint', $this->fingerprint());
		}
		else {
			$db->query('UPDATE `sessions` SET `time` = NOW() WHERE `id` = \''.session_id().'\' AND `fingerprint` = \''.$this->get('ss_fingerprint').'\' LIMIT 1;');
		}
	}
	public function __destruct(){
		global $db;
		
		$db->query('DELETE FROM `session_vars` WHERE `id` = \''.session_id().'\' AND `fingerprint` = \''.$this->get('ss_fingerprint').'\';');
		$db->query('DELETE FROM `sessions` WHERE `id` = \''.session_id().'\';');
		
		session_destroy();
	}
	public function isValid(){
		global $db;
		
		if($this->get('ss_fingerprint') === $this->fingerprint()){
			$db->query('SELECT `id`, `fingerprint` FROM `sessions` WHERE `id` = \''.session_id().'\' AND `fingerprint` = \''.$this->get('ss_fingerprint').'\';');
			if($db->num_rows() === 1) return TRUE;
			else return FALSE;
		}
		else return FALSE;
	}
	public function set($name, $value){
		global $db;
		
		$result = $db->query('INSERT INTO `session_vars` (`id`, `fingerprint`, `name`, `value`) VALUES(\''.session_id().'\', \''.$this->fingerprint().'\', \''.mysql_real_escape_string($name).'\', \''.mysql_real_escape_string($value).'\') LIMIT 1;');
		if($db->num_rows($result) === 1) return TRUE;
		else return FALSE;
	}
	public function get($name){
		global $db;
		
		$result = $db->query('SELECT `value` FROM `session_vars` WHERE `id` = \''.session_id().'\' AND `fingerprint` = \''.$this->fingerprint().'\' AND `name` = \''.mysql_real_escape_string($name).'\' LIMIT 1;');
		if($db->num_rows($result) === 1){
			$row = $db->fetch_array($result);
			
			return $row['value'];
		}
		else return NULL;
	}
	public function fingerprint(){
		$fingerprint = $this->secureWord;
		if($this->checkBrowser) $fingerprint .= $_SERVER['HTTP_USER_AGENT'];

		if ($this->checkIPBlocks){
			$num_blocks = abs(intval($this->checkIPBlocks));
			if($num_blocks > 4){
				$num_blocks = 4;
			}
			$blocks = explode('.', $_SERVER['REMOTE_ADDR']);
			for($i = 0; $i < $num_blocks; $i++){
				$fingerprint .= $blocks[$i] . '.';
			}
        }
		return md5($fingerprint);
	}
}
?>