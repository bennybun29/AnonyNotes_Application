<?php
class User {
    private $conn;
    private $table = 'users';

    public $user_id;
    public $user_name;
    public $email;
    public $password;
    public $created_at;
    public $profile_img;
    public $bio;

    public function __construct($db) {
        $this->conn = $db;
    }

    public function create() {
        $query = "INSERT INTO " . $this->table . " 
                  (user_name, email, password, created_at, profile_img, bio) 
                  VALUES (:user_name, :email, :password, :created_at, :profile_img, :bio)";
        $stmt = $this->conn->prepare($query);

        $stmt->bindParam(":user_name", $this->user_name);
        $stmt->bindParam(":email", $this->email);
        $stmt->bindParam(":password", $this->password);
        $stmt->bindParam(":created_at", $this->created_at);
        $stmt->bindParam(":profile_img", $this->profile_img);
        $stmt->bindParam(":bio", $this->bio);

        return $stmt->execute();
    }
    
    // Update User details
    public function update() {
        $query = "UPDATE " . $this->table . " SET user_name = ?, email = ?, password = ?, bio = ?, profile_img = ? WHERE user_id = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([$this->user_name, $this->email, $this->password, $this->bio, $this->profile_img, $this->user_id]);
    }

    // Delete user
    public function delete() {
        $query = "DELETE FROM " . $this->table . " WHERE user_id = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([$this->user_id]);
    }

    // Login 
    public function login($email, $password) {
        $query = "SELECT * FROM " . $this->table . " WHERE email = :email AND password = :password";
        $stmt = $this->conn->prepare($query);

        $stmt->bindParam(":email", $email);
        $stmt->bindParam(":password", $password);

        $stmt->execute();

        return $stmt->rowCount() > 0;
    }

    //Get User by Email
    public function getUserByEmail($email) {
        $query = "SELECT * FROM " . $this->table . " WHERE email = :email";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":email", $email);
        $stmt->execute();

        return $stmt->fetch(PDO::FETCH_ASSOC);
    }

    // Get all User
    public function readAll() {
        $query = "SELECT * FROM " . $this->table;
        $stmt = $this->conn->query($query);

        return $stmt->fetchAll(PDO::FETCH_ASSOC); // Returns all users as an associative array
    }

}
?>
