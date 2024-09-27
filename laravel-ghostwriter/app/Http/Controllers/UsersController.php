<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class UsersController extends Controller
{
    public function index(): Returntype
    {
        return view('users.index');
    }

    public function create() : Returntype 
    {
        return view('users.create');
    }

    public function show() : Returntype 
    {
        return view('users.show');
    }

    public function edit() : Returntype 
    {
        return view('users.edit');    
    }
}
