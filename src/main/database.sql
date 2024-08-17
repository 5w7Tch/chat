drop database if exists chatDB;
create database chatDB;

USE chatDB;

drop table if exists friendRequests;
drop table if exists friends;
drop table if exists notes;
drop table if exists announcements;
drop table if exists users;

create table if not exists users(
    userId int primary key auto_increment,
    firstName nvarchar(50) unique not null,
    email nvarchar(50) not null,
    passwordHash nvarchar(100) not null,
    isAdmin bool not null default false,
    linkToImage nvarchar(256) default 'https://www.gravatar.com/avatar/?d=mp&s=150&f=y'
);

create table if not exists friendRequests(
      requestId int primary key auto_increment,
      fromUserId int not null,
      toUserId int not null,
      sendTime DATETIME NOT NULL,
      foreign key (fromUserId) references users(userId),
      foreign key (toUserId) references users(userId)
);

create table if not exists friends(
      friendId int primary key auto_increment,
      user1Id int not null,
      user2Id int not null,
      timeStamp DATE NOT NULL,
      foreign key (user1Id) references users(userId),
      foreign key (user2Id) references users(userId)
);

CREATE TABLE if not exists notes (
     noteId INT AUTO_INCREMENT PRIMARY KEY,
     fromId INT NOT NULL,
     toId INT NOT NULL,
     text TEXT NOT NULL,
     sendTime DATETIME NOT NULL,
     FOREIGN KEY (fromId) REFERENCES users(userId),
     FOREIGN KEY (toId) REFERENCES users(userId)
);



CREATE TABLE if not exists announcements(
    announcementId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    text TEXT NOT NULL,
    timeStamp DATE NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(userId)
);



insert into users values (1,'nika','nika@', '34bff7be484da58a7c244a79ef278630f334a732',  true, 'https://www.gravatar.com/avatar/?d=mp&s=150&f=y');
insert into users values (2,'lasha','lasha@', 'ee5d0f40184e345d01bf17e5a8a8dab7bcf0c4c8',  true, 'https://www.gravatar.com/avatar/?d=mp&s=150&f=y');
insert into users values (3,'zoro','zoro@', '2cdb8c4253053c1e0a8bcba9c9b482aec983bf55',  false,'https://www.gravatar.com/avatar/?d=mp&s=150&f=y');
insert into friends values (1,1,2, sysdate());

