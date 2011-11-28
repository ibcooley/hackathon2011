<?php

namespace Simplevent\Models;

/**
 * An Item that has been requested to be brought to the party
 */
class Item
{
    /**
     * @var int
     */
    private $id;

    /**
     * @var int
     */
    private $created_by_user_id;

    /**
     * @var int
     */
    private $claimed_by_user_id;

    /**
     * @var int
     */
    private $event_id;

    /**
     * @var string
     */
    private $date_created;

    /**
     * @var string
     */
    public $title;

    /**
     * @var string
     */
    public $description;

    const NOT_CLAIMED_BY_USER_ID = 0;

    const ID_OF_UNKNOWN_ITEM = 0;

    private function __construct($id)
    {
        $db = \Walleye\Database::getInstance();
        $get_item_stmt = $db->prepare('SELECT title, description, created_by_user_id, claimed_by_user_id, event_id, date_created FROM Items WHERE id = ?');
        $get_item_stmt->bind_param('i', $id);
        $get_item_stmt->execute();

        if ($item_row = $db->getRow($get_item_stmt)) {
            $this->id = $id;
            $this->title = $item_row->title;
            $this->desc = $item_row->description;
            $this->created_by_user_id = $item_row->created_by_user_id;
            $this->claimed_by_user_id = $item_row->claimed_by_user_id;
            $this->event_id = $item_row->event_id;
            $this->date_created = $item_row->date_created;
        }
        else {
            $this->id = Item::ID_OF_UNKNOWN_ITEM;
        }
    }

    /**
     * @static
     * @param $id
     * @return null|Item
     */
    public static function withId($id)
    {
        $instance = new Item($id);
        if ($instance->getId() != Item::ID_OF_UNKNOWN_ITEM) {
            return $instance;
        }
        return null;
    }

    /**
     * @static
     * @param $title
     * @param $event_id
     * @param null $created_by_user_id
     * @return null|Item
     */
    public static function create($title, $event_id, $created_by_user_id = null)
    {
        if (is_null($created_by_user_id) == null) {
            $created_by_user_id = User::getLoggedUser()->getId();
        }

        $db = \Walleye\Database::getInstance();
        $insert_item_stmt = $db->prepare('INSERT INTO Items (title, event_id, $created_by_user_id) VALUES (?, ?, ?)');
        $insert_item_stmt->bind_param('sii', $title, $event_id, $created_by_user_id);
        if ($insert_item_stmt->execute() == true) {
            $item_id = $db->insert_id;
            return Item::withId($item_id);
        }
        return null;
    }

    /**
     * Commits any changes done to the public properties to the db
     *
     * @return bool
     */
    public function commit()
    {
        $db = \Walleye\Database::getInstance();
        $update_item_stmt = $db->prepare('UPDATE Items SET title = ?, desc = ? WHERE id = ?');
        $update_item_stmt->bind_param('ssi', $this->title, $this->desc, $this->getId());
        return $update_item_stmt->execute();
    }

    /**
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @return string
     */
    public function getTitle()
    {
        return $this->title;
    }

    /**
     * @return string
     */
    public function getDesc()
    {
        return $this->description;
    }

    /**
     * @return null|Event
     */
    public function getEvent()
    {
        return Event::withId($this->event_id);
    }

    /**
     * @return null|User
     */
    public function getCreatedByUser()
    {
        return User::withId($this->created_by_user_id);
    }

    /**
     * @return null|User
     */
    public function getClaimedByUser()
    {
        return User::withId($this->claimed_by_user_id);
    }

    /**
     * @return string
     */
    public function getDateCreated()
    {
        return $this->date_created;
    }

    /**
     * @param int $user_id
     * @return bool
     */
    public function setClaimedByUser($user_id)
    {
        $db = \Walleye\Database::getInstance();
        $update_item_stmt = $db->prepare('UPDATE Items SET claimed_by_user_id = ? WHERE id = ?');
        $update_item_stmt->bind_param('ii', $user_id, $this->getId());
        if ($update_item_stmt->execute() == true) {
            $this->claimed_by_user_id = $user_id;
            return true;
        }
        return false;
    }

    /**
     * @return null|User
     */
    public function getUserWhoClaimed()
    {
        return User::withId($this->claimed_by_user_id);
    }

    /**
     * @return bool
     */
    public function isClaimed()
    {
        if ($this->claimed_by_user_id != Item::NOT_CLAIMED_BY_USER_ID) return true;
        return false;
    }
}

/* End of file */
