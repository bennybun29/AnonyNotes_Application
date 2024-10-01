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
        // Create the comments table
        Schema::create('comments', function (Blueprint $table) {
            $table->id('comment_id'); // Primary key 
            $table->unsignedBigInteger('note_id'); // Foreign key -> notes table
            $table->string('user_name', 50); // Foreign key -> users table (user_name)
            $table->text('content'); // Comment content
            $table->boolean('anonymous'); // 1 = Anonymous 0 = user_name visible
            $table->timestamps(); // Date when the comment was created

            // Foreign key constraints
            $table->foreign('note_id')
                  ->references('note_id')
                  ->on('notes')
                  ->onDelete('cascade')
                  ->onUpdate('cascade');

            $table->foreign('user_name')
                  ->references('user_name')
                  ->on('users')
                  ->onDelete('cascade')
                  ->onUpdate('cascade');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('comments');
    }
};
