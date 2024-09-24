<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Methods: POST");
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
}
?>
