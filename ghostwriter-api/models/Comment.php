<?php
class Note {
    private $conn;
    private $table = 'notes';

    public $note_id;
    public $user_name;
    public $content;
    public $anonymous;
    public $created_at;

    public function __construct($db) {
        $this->conn = $db;
    }

    public function create() {
        $query = "INSERT INTO " . $this->table . " 
                  (user_name, content, anonymous, created_at) 
                  VALUES (:user_name, :content, :anonymous, :created_at)";
        $stmt = $this->conn->prepare($query);

        $stmt->bindParam(":user_name", $this->user_name);
        $stmt->bindParam(":content", $this->content);
        $stmt->bindParam(":anonymous", $this->anonymous);
        $stmt->bindParam(":created_at", $this->created_at);

        return $stmt->execute();
    }

    public function read() {
        $query = "SELECT * FROM " . $this->table;
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt;
    }
}
?>
