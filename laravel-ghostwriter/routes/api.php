<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UsersController;
use App\Http\Controllers\NotesController;
use App\Http\Controllers\CommentsController;
use App\Http\Controllers\HeartsController;

// Authentication routes for registering and logging in users
Route::post('register', [UsersController::class, 'register']); // Route for user registration
Route::post('login', [UsersController::class, 'login']); // Route for user login
Route::get('notes', [NotesController::class, 'index']);



// Protect the following routes using Sanctum middleware (requires authentication)
Route::middleware('auth:sanctum')->group(function () {

    // Routes for User CRUD operations
    Route::resource('users', UsersController::class)->except(['register', 'login']);

    // Routes for Notes CRUD operations
    Route::resource('notes', NotesController::class);

    // Routes for Comments CRUD operations
    Route::resource('comments', CommentsController::class);

    // Routes for Hearts (likes) CRUD operations
    Route::resource('hearts', HeartsController::class);

    // Route for logging out the authenticated user
    Route::post('logout', [UsersController::class, 'logout']);
});
