<?php
if (!defined('SECURITY')){
	include_once(HOME_DIR.DIRECTORY_SEPARATOR.'includes'.DIRECTORY_SEPARATOR.'functions.php');
	error(403, __FILE__, __LINE__);
}
class Settings {
	private $template_id       = 0;
	private $template_name     = '';
	private $template_location = '';
	
	public function __construct($template_id){
		global $db, $page;

		$result = $db->query('SELECT t.name AS `name`, t.path AS `path`, tm.name AS `name`, tm.value AS `value`, tm.order AS `order` FROM `template_meta` tm, `templates` t WHERE t.id = \''.$template_id.'\' AND tm.template_id = \''.$template_id.'\' OR tm.template_id = \'0\' ORDER BY tm.order;');
		while($row = $db->fetch_array($result)){
			if($this->template_id === 0){
				$this->template_id       = $template_id;
				$this->template_name     = $row['name'];
				$this->template_location = $row['path'];
				
				define('INCLUDES_TEMPLATE', HOME_DIR.DIRECTORY_SEPARATOR.'templates'.DIRECTORY_SEPARATOR.$row['path'].DIRECTORY_SEPARATOR);
			}
			switch($row['name']){
				case 'meta' :
					list($type, $inTemplate, $link) = explode(',', $row['value']);
					$inTemplate = boolval($inTemplate);
					$page->addMeta($type, (($inTemplate) ? '/templates/'.$row['path'].'/'.$link : $link));
				break;
			}
		}
	}	
	public function getMeta(){
		return $this->meta;
	}
}
?>