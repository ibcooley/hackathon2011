<?
if (!defined('SECURITY')){
	include_once(HOME_DIR.DIRECTORY_SEPARATOR.'includes'.DIRECTORY_SEPARATOR.'functions.php');
	error(403, __FILE__, __LINE__);
}
class page {
	private $url         = '';
	private $query       = array();
	private $queryCount  = 0;
	private $title       = '';
	private $meta        = array(
			'css' => array(),
			'js'  => array()
		);
	private $isAPI       = FALSE;
	
	public function __construct(){
		global $db, $visitor;
		
		$this->url        = (substr($_SERVER['REQUEST_URI'], -1) === '/') ? $_SERVER['REQUEST_URI'] : $_SERVER['REQUEST_URI'].'/';
		$this->query      = (substr($_SERVER['REQUEST_URI'], -1) === '/') ? explode('/', substr(str_replace('.', '', $_SERVER['REQUEST_URI']), 1, -1)) : explode('/', substr(str_replace('.', '', $_SERVER['REQUEST_URI']), 1));
		$this->queryCount = count($this->query);
		$this->isAPI      = (isset($this->query[0]) && strtolower($this->query['0']) === 'api') ? TRUE : FALSE;
	}
	public function __toString(){
		global $db, $visitor, $page, $session;
		
		if(!isset($this->query[0]) || (isset($this->query[0]) && ($this->query[0] === ''))){
			if(file_exists(INCLUDES_PAGES.'index.page.php')) include_once(INCLUDES_PAGES.'index.page.php');
			else error(404, __FILE__, __LINE__);
		}
		else if(isset($this->query[0]) && ($this->query[0] !== '')){
			if(file_exists(INCLUDES_PAGES.$this->query[0].'.page.php')) include_once(INCLUDES_PAGES.$this->query[0].'.page.php');
			else error(404, __FILE__, __LINE__);
			
		}
		else error(404, __FILE__, __LINE__);
		
		return '';
	}
	public function getURL(){
		return $this->url;
	}
	public function getQuery($num = NULL){
		if($num === NULL) return $this->query;
		else if((intval($num) > -1) && (intval($num) < count($this->query)) && array_key_exists(intval($num), $this->query)) return $this->query[intval($num)];
		else return FALSE;
	}
	public function getQueryCount(){
		return $this->queryCount;
	}
	public function setTitle($title){
		$this->title = $title;
	}
	public function getTitle(){
		return $this->title;
	}
	public function title(){
		echo $this->title;
	}
	public function getMeta(){
		echo "\t".'<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />'."\r\n";
		foreach($this->meta['js'] AS $js){
			echo "\t".'<script src="'.$js.'" type="text/javascript"></script>'."\r\n";
		}
		foreach($this->meta['css'] AS $css){
			echo "\t".'<link href="'.$css.'" media="all" rel="stylesheet" type="text/css" />'."\r\n";
		}
	}
	public function addMeta($type, $link){
		if(($type === 'css') && (strlen($link) > 4)){
			$this->meta['css'][] = $link;
		}
		else if(($type === 'js') && (strlen($link) > 3)){
			$this->meta['js'][] = $link;
		}
	}
	public function isAPI(){
		return $this->isAPI;
	}
	public function header(){
		global $db, $visitor, $page;
		
		if(file_exists(INCLUDES_TEMPLATE.'header.php')) include_once(INCLUDES_TEMPLATE.'header.php');
	}
	public function content(){
		echo str_replace("\n", "\n\t", $this->content)."\r\n";
	}
	public function getContent(){
		return str_replace("\n", "\n\t", $this->content)."\r\n";
	}
	public function footer(){
		global $db, $visitor, $page;
		if(file_exists(INCLUDES_TEMPLATE.'footer.php')) include_once(INCLUDES_TEMPLATE.'footer.php');
	}
}
?>