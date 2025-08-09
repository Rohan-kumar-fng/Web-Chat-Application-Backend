-- Use the database
USE chat_app_db;

-- Create users table
CREATE TABLE users (
    id Binary(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID(),1)),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE,
    mobileNo VARCHAR(15) UNIQUE,
    password_hash VARCHAR(255),
    bio VARCHAR(255),
    status ENUM('ONLINE', 'OFFLINE', 'AWAY') DEFAULT 'OFFLINE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create chat_rooms table
CREATE TABLE chat_rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type ENUM('PUBLIC', 'PRIVATE', 'GROUP') NOT NULL,
    created_by Binary(16),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

-- Create messages table
CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    sender_id Binary(16) NOT NULL,
    chat_room_id BIGINT,
    receiver_id Binary(16), -- For private messages
    message_type ENUM('TEXT', 'IMAGE', 'FILE', 'AUDIO', 'VIDEO') DEFAULT 'TEXT',
    status ENUM('SENT', 'DELIVERED', 'READ') DEFAULT 'SENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (chat_room_id) REFERENCES chat_rooms(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_messages_sender ON messages(sender_id);
CREATE INDEX idx_messages_receiver ON messages(receiver_id);
CREATE INDEX idx_messages_chat_room ON messages(chat_room_id);
CREATE INDEX idx_messages_created_at ON messages(created_at);

-- Show tables to verify
SHOW TABLES;--