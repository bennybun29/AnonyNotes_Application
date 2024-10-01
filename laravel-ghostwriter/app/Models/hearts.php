<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Hearts extends Model
{
    use HasFactory;

    // Specify the table associated with this model
    protected $table = 'hearts';
    protected $primaryKey = 'heart_id'; // Specify the primary key for the table

    // Define fillable fields for mass assignment
    protected $fillable = [
        'heart_id',
        'comment_id',
        'note_id',
        'user_name',
        'created_at',
    ];

    //Relationships with other tables

    // Define the relationship with the `Users` model (Each heart belongs to a user)
    public function user()
    {
        return $this->belongsTo(Users::class, 'user_name', 'user_name');
    }

    // Define the relationship with the `Notes` model (Each heart can be associated with a note)
    public function note()
    {
        return $this->belongsTo(Notes::class, 'note_id');
    }

    // Define the relationship with the `Comments` model (Each heart can be associated with a comment)
    public function comment()
    {
        return $this->belongsTo(Comments::class, 'comment_id');
    }
}
