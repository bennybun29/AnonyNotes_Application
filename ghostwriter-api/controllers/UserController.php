<?php
require '../models/User.php';
require '../config/database.php';
class UserController {
    private $user;

    public function __construct() {
        $this->user = new User((new Database())->getConnection());
    }

    // Register a user
    public function register($data) {
        $this->user->user_name = $data['user_name'];
        $this->user->email = $data['email'];
        $this->user->password = $data['password']; // No hashing
        $this->user->created_at = date('Y-m-d H:i:s');
        $this->user->profile_img = $data['profile_img'] ?? null; // Optional
        $this->user->bio = $data['bio'] ?? null; // Optional

        if ($this->user->create()) {
            echo json_encode(['message' => 'User registered successfully.']);
        } else {
            echo json_encode(['message' => 'User registration failed.']);
        }
    }

    // Login a user
    public function login($data) {
        $email = $data['email'];
        $password = $data['password'];

        if ($this->user->login($email, $password)) {
            echo json_encode(['message' => 'Login successful.']);
        } else {
            echo json_encode(['message' => 'Invalid email or password.']);
        }
    }

    // Update user details
    public function updateUser($data) {
        $this->user->user_id = $data['user_id'];
        $this->user->user_name = $data['user_name'];
        $this->user->email = $data['email'];
        $this->user->password = $data['password']; // No hashing
        $this->user->bio = $data['bio'] ?? null; // Optional
        $this->user->profile_img = $data['profile_img'] ?? null; // Optional

        $this->user->update();
        echo json_encode(['message' => 'User details updated.']);
    }

    // Delete a user
    public function deleteUser($data) {
        $this->user->user_id = $data['user_id'];
        $this->user->delete();
        echo json_encode(['message' => 'User deleted.']);
    }

    // Get all users
    public function getAllUsers() {
        $users = $this->user->readAll();
        echo json_encode($users);
    }

    // Get user by email
    public function getUserByEmail($data) {
        $user = $this->user->getUserByEmail($data['email']);
        echo json_encode($user);
    }
}
?>
