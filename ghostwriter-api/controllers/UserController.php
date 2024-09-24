<?php
include_once '../config/Database.php';
include_once '../models/User.php';

class UserController {
    private $db;
    private $user;

    public function __construct() {
        $database = new Database();
        $this->db = $database->getConnection();
        $this->user = new User($this->db);
    }

    public function register($data) {
        $this->user->user_name = $data['user_name'];
        $this->user->email = $data['email'];
        $this->user->password = $data['password'];
        $this->user->created_at = date('Y-m-d');
        $this->user->profile_img = isset($data['profile_img']) ? $data['profile_img'] : null;
        $this->user->bio = isset($data['bio']) ? $data['bio'] : null;

        if($this->user->create()) {
            echo json_encode(['message' => 'User registered successfully.']);
        } else {
            echo json_encode(['message' => 'User registration failed.']);
        }
    }

    public function login($data) {
        if ($this->user->login($data['email'], $data['password'])) {
            $userData = $this->user->getUserByEmail($data['email']);
            echo json_encode($userData);
        } else {
            echo json_encode(['message' => 'Login failed.']);
        }
    }
}
?>
