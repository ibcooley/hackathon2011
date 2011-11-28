<?php

namespace Simplevent\Models;

/**
 * Represents a comment on an Event
 */
class Comment
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
    private $event_id;

    /**
     * @var string
     */
    private $date_created;

    /**
     * @var string
     */
    public $comment;

    const ID_OF_UNKNOWN_COMMENT = 0;

    private function __construct($id)
    {
        $db = \Walleye\Database::getInstance();
        $get_comment_stmt = $db->prepare('SELECT comment, event_id, created_by_user_id, date_created FROM Comments WHERE id = ?');
        $get_comment_stmt->bind_param('i', $id);
        $get_comment_stmt->execute();

        if ($comment_row = $db->getRow($get_comment_stmt)) {
            $this->id = $id;
            $this->comment = $comment_row->comment;
            $this->event_id = $comment_row->event_id;
            $this->created_by_user_id = $comment_row->created_by_user_id;
            $this->date_created = $comment_row->date_created;
        }
        else {
            $this->id = Comment::ID_OF_UNKNOWN_COMMENT;
        }
    }

    /**
     * @static
     * @param int $id
     * @return null|Comment
     */
    public static function withId($id)
    {
        $instance = new Comment($id);
        if ($instance->getId() == Comment::ID_OF_UNKNOWN_COMMENT) {
            return null;
        }
        return $instance;
    }

    /**
     * @static
     * @param $comment
     * @param $event_id
     * @param null $created_by_user_id
     * @return null|Comment
     */
    public static function create($comment, $event_id, $created_by_user_id = null)
    {
        // if no user is passed, then use the logged user
        if (is_null($created_by_user_id) == true) {
            $created_by_user_id = User::getLoggedUser()->getId();
        }

        $db = \Walleye\Database::getInstance();
        $insert_comment_stmt = $db->prepare('INSERT INTO Comments (comment, event_id, created_by_user_id) VALUES (?, ?, ?)');
        $insert_comment_stmt->bind_param('sii', $comment, $event_id, $created_by_user_id);
        if ($insert_comment_stmt->execute() == true) {
            $comment_id = $db->insert_id;
            return Comment::withId($comment_id);
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
        $update_comment_stmt = $db->prepare('UPDATE Comments SET comment = ? WHERE id = ?');
        $update_comment_stmt->bind_param('si', $this->comment, $this->id);
        return $update_comment_stmt->execute();
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
    public function getComment()
    {
        return $this->comment;
    }

    /**
     * @return Event
     */
    public function getEvent()
    {
        return Event::withId($this->event_id);
    }

    /**
     * @return User
     */
    public function getCreatedByUser()
    {
        return User::withId($this->created_by_user_id);
    }

    /**
     * @return string
     */
    public function getDateCreated()
    {
        return $this->date_created;
    }

    /**
     * @return bool
     */
    public function remove()
    {
        $db = \Walleye\Database::getInstance();
        $remove_comment_stmt = $db->prepare('DELETE FROM Comments WHERE id = ?');
        $remove_comment_stmt->bind_param('i', $this->getId());
        return $remove_comment_stmt->execute();
    }

}
 
/* End of file */
