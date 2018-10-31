var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var session = require('express-session');

var index = require('./routes/home/index');
// var users = require('./routes/users');
var posts = require('./routes/home/posts');
var admin = require('./routes/admin/index');
var cats = require('./routes/admin/cats');
var article = require('./routes/admin/posts');

var user = require('./routes/admin/user');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'views/admin')));
app.use(session({
	secret: 'blog',
  	resave: false,
  	saveUninitialized: true,
  	cookie: {}
}));

//判断是否已经登录
app.use("/admin",(req,res,next) => {
	if (!req.session.isLogin) {
		//没有登录，跳转到登录的页面
		res.redirect('/user/login');
		return;
	}
	next();
});

app.use('/', index);
// app.use('/users', users);
//文章页面的路由
app.use('/posts',posts);

//后台首页的路由
app.use('/admin',admin);
//后台分类管理的一级路由
app.use('/admin/cats',cats);
//后台文章管理的一级路由
app.use('/admin/posts',article);

//后台用户管理的一级路由
app.use("/user",user);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
