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

    // Add Heart
    public function create() {
        $query = "INSERT INTO " . $this->table . " 
                  (comment_id, note_id, user_name, created_at) 
                  VALUES (:comment_id, :note_id, :user_name, NOW())";
        $stmt = $this->conn->prepare($query);

        $stmt->bindParam(":comment_id", $this->comment_id);
        $stmt->bindParam(":note_id", $this->note_id);
        $stmt->bindParam(":user_name", $this->user_name);

        return $stmt->execute();
    }

    // Read all hearts for a specific note or comment
    public function readByNoteOrComment($note_id, $comment_id) {
        $query = "SELECT * FROM " . $this->table . " WHERE note_id = :note_id OR comment_id = :comment_id";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':note_id', $note_id);
        $stmt->bindParam(':comment_id', $comment_id);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC); // Returns all hearts for the note or comment
    }

    // Remove a heart
    public function delete() {
        $query = "DELETE FROM " . $this->table . " WHERE heart_id = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([$this->heart_id]);
    }
}
?>
