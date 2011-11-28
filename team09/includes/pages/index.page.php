<?php
if (!defined('SECURITY')){
	include_once(HOME_DIR.DIRECTORY_SEPARATOR.'includes'.DIRECTORY_SEPARATOR.'functions.php');
	error(403, __FILE__, __LINE__);
}
if(($page->getQueryCount() === 1) && ($page->getQuery(0) === '')){
	if(($visitor !== NULL) && ($visitor->isLoggedIn())){
		header('Location: /user/'.$session->get('username'));
	}
	else {
		header('Location: /login');
	}
}
else error(404, __FILE__, __LINE__);
?>