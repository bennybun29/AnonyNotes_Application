<?php
class Comment {
    private $conn;
    private $table = 'comment';

    public $comment_id;
    public $note_id;
    public $user_name;
    public $content;
    public $anonymous;
    public $created_at;

    public function __construct($db) {
        $this->conn = $db;
    }

    // Create a comment
    public function create() {
        $query = "INSERT INTO " . $this->table . " 
                  (note_id, user_name, content, anonymous, created_at) 
                  VALUES (:note_id, :user_name, :content, :anonymous, :created_at)";
        $stmt = $this->conn->prepare($query);

        $stmt->bindParam(":note_id", $this->note_id);
        $stmt->bindParam(":user_name", $this->user_name);
        $stmt->bindParam(":content", $this->content);
        $stmt->bindParam(":anonymous", $this->anonymous);
        $stmt->bindParam(":created_at", $this->created_at);

        return $stmt->execute();
    }

    // Read all comments for a note
    public function read() {
        $query = "SELECT * FROM " . $this->table . " WHERE note_id = :note_id";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":note_id", $this->note_id);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC); // Returns all comments for the note
    }

    // Update a comment
    public function update() {
        $query = "UPDATE " . $this->table . " SET content = ?, anonymous = ? WHERE comment_id = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([$this->content, $this->anonymous, $this->comment_id]);
    }

    // Delete a comment
    public function delete() {
        $query = "DELETE FROM " . $this->table . " WHERE comment_id = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([$this->comment_id]);
    }
}
?>
