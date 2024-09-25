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

    //Create a note
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
    
    // Read all notes
    public function read() {
        $query = "SELECT * FROM " . $this->table;
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt;
    }

     // Read a note by ID
     public function read($id) {
        $query = "SELECT * FROM " . $this->table . " WHERE note_id = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([$id]);

        return $stmt->fetch(PDO::FETCH_ASSOC); // Returns the note's data
    }

    // Update a note
    public function update() {
        $query = "UPDATE " . $this->table . " SET content = ?, anonymous = ? WHERE note_id = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([$this->content, $this->anonymous, $this->note_id]);
    }

       // Delete a note
       public function delete() {
        $query = "DELETE FROM " . $this->table . " WHERE note_id = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([$this->note_id]);
    }

}
?>
