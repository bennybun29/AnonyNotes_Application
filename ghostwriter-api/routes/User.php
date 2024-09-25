<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Methods: POST, GET, PUT, DELETE");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../controllers/UserController.php';

$userController = new UserController();
$data = json_decode(file_get_contents("php://input"), true);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($data['action']) && $data['action'] === 'register') {
        $userController->register($data);
    } elseif (isset($data['action']) && $data['action'] === 'login') {
        $userController->login($data);
    } else {
        echo json_encode(['message' => 'Invalid action.']);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'PUT') {
    $userController->updateUser($data);
} elseif ($_SERVER['REQUEST_METHOD'] === 'DELETE') {
    $userController->deleteUser($data);
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET') {
    if (isset($data['email'])) {
        $userController->getUserByEmail($data);
    } else {
        $userController->getAllUsers();
    }
}
?>
