<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        // Users table creation
        Schema::create('users', function (Blueprint $table) {
            $table->id('user_id'); // Primary key 
            $table->string('user_name', 50)->unique(); // user_name with unique constraint
            $table->string('email', 50)->unique(); // Email with unique constraint
            $table->string('password'); 
            $table->binary('profile_img')->nullable(); // Profile image as BLOB
            $table->string('bio', 500)->nullable(); // Bio field, limit of 500 characters
            $table->rememberToken(); // Token for remember me function
            $table->timestamps(); // Timestamps handles created_at and updated_at
            
        });

        // Password reset tokens table
        Schema::create('password_reset_tokens', function (Blueprint $table) {
            $table->string('email')->primary();
            $table->string('token');
            $table->timestamp('created_at')->nullable();
        });

        // Sessions table
        Schema::create('sessions', function (Blueprint $table) {
            $table->string('id')->primary();
            $table->foreignId('user_id')->nullable()->index();
            $table->string('ip_address', 45)->nullable();
            $table->text('user_agent')->nullable();
            $table->longText('payload');
            $table->integer('last_activity')->index();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('users');
        Schema::dropIfExists('password_reset_tokens');
        Schema::dropIfExists('sessions');
    }
};
