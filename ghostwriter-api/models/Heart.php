<?php
class Heart {
    private $conn;
    private $table = 'hearts';

    public $heart_id;
    public $comment_id;
    public $note_id;
    public $user_name;
    public $created_at;

    public function __construct($db) {
        $this->conn = $db;
    }

    public function create() {
        $query = "INSERT INTO " . $this->table . " 
                  (comment_id, note_id, user_name, created_at) 
                  VALUES (:comment_id, :note_id, :user_name, :created_at)";
        $stmt = $this->conn->prepare($query);

        $stmt->bindParam(":comment_id", $this->comment_id);
        $stmt->bindParam(":note_id", $this->note_id);
        $stmt->bindParam(":user_name", $this->user_name);
        $stmt->bindParam(":created_at", $this->created_at);

        return $stmt->execute();
    }
}
?>
