<?
if (!defined('SECURITY')){
	include_once 'functions.php';
	error(403, __FILE__, __LINE__);
}
class Visitor {		
	public $id = '';
	public $username = '';
	
	public function __construct($hash = NULL){
		global $db, $session;
		
		$result = $db->query('SELECT * FROM `users` WHERE `username` = (SELECT `value` FROM `session_vars` WHERE `id` = \''.session_id().'\' AND `fingerprint` = \''.$session->get('ss_fingerprint').'\' AND `name` = \'username\') LIMIT 1;');
		if($db->num_rows($result) === 1){
			$this->id = $row['id'];
			$this->username = $row['username'];
		}
	}
	public function isLoggedIn(){
		global $session;
		
		return boolval($session->get('logged_in'));
	}
	public function isUser(){
		global $session;
		
		return boolval($session->get('is_user'));
	}
	public function isDJ(){
		global $session;
		
		return boolval($session->get('is_dj'));
	}
	public function getUsername(){
		global $session;
		
		return $session->get('username');
	}
	public function login($data){
		global $db, $visitor, $session;
		
		if(!(isset($data['username']) && ($data['username'] !== ''))) error_json('Please enter your username.', __FILE__, __LINE__);
		else if(!(isset($data['password']) && ($data['password'] !== ''))) error_json('Please enter your password.', __FILE__, __LINE__);
		else {
			$result = $db->query('SELECT * FROM `users` WHERE `username` = \''.mysql_real_escape_string($data['username']).'\' AND `password` = \''.md5(SALT_PASSWORD.mysql_real_escape_string($data['password']).SALT_PASSWORD).'\' LIMIT 1;');
			if($db->num_rows($result) === 1){
				$row = $db->fetch_array($result);
				
				$session->set('logged_in', TRUE);
				$session->set('username', $row['username']);
				
				output_json(array('error' => 'false', 'href' => '/user/'.$row['username']));
			}
			else error_json('Invalid username/password combination.', __FILE__, __LINE__);
		}
	}
	public function logout(){
		global $db, $session;
		
		unset($session);
		
		header('Location: /');
	}
	public function register($data){
		global $db;
		
		if(!(isset($data['account_type']) && (strlen($data['account_type']) !== 0) && ((strtolower($data['account_type']) === 'dj') || (strtolower($data['account_type']) === 'user')))) error_json('Please select a valid user type.', __FILE__, __LINE__);
		else $data['account_type'] = strtolower($data['account_type']);
		
		if(!(isset($data['username']) && (strlen($data['username']) !== 0))) error_json('Please enter a username.', __FILE__, __LINE__);
		else if(!((strlen($data['username']) >= 3) && (strlen($data['username']) <= 32))) error_json('Username must be between 3 and 32 characters.', __FILE__, __LINE__); 
		else if(!preg_match('/^[A-Za-z0-9]*$/', $data['username'])) error_json('Username must consist of only the characters A-Z, a-z, and 0-9.', __FILE__, __LINE__);
		else {
			$result = $db->query( 'SELECT count(*) FROM `users` WHERE `username` = \''.strtolower($data['username']).'\';');
			if($db->num_rows($result) !== 0) error_json('Username is already taken.', __FILE__, __LINE__);
			else $data['username'] = strtolower($data['username']);
		}
		
		if(!(isset($data['email']) && (strlen($data['email']) !== 0))) error_json('Please enter your email address.', __FILE__, __LINE__);
		else if(!preg_match('/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/', $data['email'])) error_json('Please enter a valid e-mail address.', __FILE__, __LINE__);
		else if(!($data['email'] !== $data['email_confirm'])) error_json('E-mail addresses do not match.', __FILE__, __LINE__);
		else {
			$result = $db->query( 'SELECT COUNT(*) FROM `users` WHERE `email` = \''.$data['email'].'\';');
			if($db->num_rows($result) !== 0) error_json('An account using this e-mail address has already been registered.', __FILE__, __LINE__);
			else $data['email'] = strtolower($data['email']);
		}
		
		if(!(isset($data['password']) && (strlen($data['password']) !== 0))) error_json('Please enter a password.', __FILE__, __LINE__);
		else if(!(strlen($data['password']) <= 5)) error_json('Password must be at least 5 characters.', __FILE__, __LINE__);
		else if(!($data['password'] === $data['password_confirm'])) error_json('Passwords must match.', __FILE__, __LINE__);
		else $data['password'] = mysql_real_escape_string($data['password']);
		
		if(!(isset($data['phone']) && (strlen($data['phone']) !== 0))) error_json('Please enter your mobile phone number.', __FILE__, __LINE__);
		else if(!preg_match('/^([0-9]{10})$/', str_replace('.', '', $data['phone']))) error_json('Please enter a valid US mobile phone number.', __FILE__, __LINE__);
		else $data['phone'] = str_replace('.', '', $data['phone']);
		
		$providers = array();
		
		$result = $db->query('SELECT * FROM `providers` ORDER BY `name`;');
		
		while($row = $db->fetch_array($result)){
			$providers[$row['id']] = array(
				'name'  => str_replace(array(' ', '&'), array('', ''), $row['name']),
				'email' => $row['email']
			);
		}
		
		if(!(isset($data['provider']) && (intval($data['provider']) !== 0) && array_key_exists(intval($data['provider']), $providers))) error_json('Please select a valid mobile phone provider.', __FILE__, __LINE__);
		else $data['provider'] = intval($data['provider']);
		
		if(!(isset($data['first_name']) && (strlen(trim($data['first_name'])) !== 0))) error_json('Please enter your first name.', __FILE__, __LINE__);
		else if(!((strlen(trim($data['first_name'])) >= 2) && (strlen(trim($data['first_name'])) <= 32))) error_json('First name must be between 2 and 32 characters.', __FILE__, __LINE__); 
		else if(!preg_match('/^[A-Za-z\s]*$/', trim($data['first_name']))) error_json('First name must consist of only the characters A-Z, a-z, and spaces.', __FILE__, __LINE__);
		
		if((isset($data['last_name']) && (strlen($data['last_name']) !== 0))){
			if(!(strlen(trim($data['last_name'])) >= 2) && (strlen(trim($data['last_name'])) <= 32)) error_json('Last name must be between 2 and 32 characters.', __FILE__, __LINE__);
			else if(!preg_match('/^[A-Za-z\s]*$/', trim($data['last_name']))) error_json('First name must consist of only the characters A-Z, a-z, and spaces.', __FILE__, __LINE__);
			else $data['last_name'] = trim($data['last_name']);
		}
		else $data['last_name'] == '';
		
		$result = $db->query( 'INSERT INTO `users` (`id`, `first_name`, `last_name`, `registration_data`, `username`, `email`, `phone`, `provider`) VALUES(\'UUID()\', \''.$data['first_name'].'\', \''.$data['last_name'].'\', timestamp(),\''.$data['username'].'\', \''.$data['email'].'\', \''.$data['phone'].'\', \''.$data['provider'].'\');');
		
		if(!($result)) error_json('Database error.', __FILE__, __LINE__);
		else output_json(array('error' => 'false', 'href' => '/login'));
	}
}
?>