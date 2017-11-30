'use strict';

var gulp = require('gulp');
var $ = require('gulp-load-plugins')();
var lazypipe = require('lazypipe');
var del = require('del');
var wiredep = require('wiredep').stream;
var runSequence = require('run-sequence');
var browserSync = require('browser-sync');

var mrmedia = {
  app: require('./bower.json').appPath || 'app',
  dist: 'dist'
};

var paths = {
  scripts: [mrmedia.app + '/scripts/**/*.js'],
  styles: [mrmedia.app + '/styles/**/*.css'],
  test: ['test/spec/**/*.js'],
  testRequire: [
    mrmedia.app + '/bower_components/angular/angular.js',
    mrmedia.app + '/bower_components/angular-mocks/angular-mocks.js',
    mrmedia.app + '/bower_components/angular-resource/angular-resource.js',
    mrmedia.app + '/bower_components/angular-cookies/angular-cookies.js',
    mrmedia.app + '/bower_components/angular-sanitize/angular-sanitize.js',
    mrmedia.app + '/bower_components/angular-route/angular-route.js',
    'test/mock/**/*.js',
    'test/spec/**/*.js'
  ],
  karma: 'karma.conf.js',
  views: {
    main: mrmedia.app + '/index.html',
    files: [mrmedia.app + '/views/**/*.html']
  }
};

////////////////////////
// Reusable pipelines //
////////////////////////

var lintScripts = lazypipe()
  .pipe($.jshint, '.jshintrc')
  .pipe($.jshint.reporter, 'jshint-stylish');

var styles = lazypipe()
  .pipe($.autoprefixer, 'last 2 version')
  .pipe(gulp.dest, '.tmp/styles');

///////////
// Tasks //
///////////

gulp.task('styles', function () {
  return gulp.src(paths.styles)
    .pipe(styles());
});

gulp.task('lint:scripts', function () {
  return gulp.src(paths.scripts)
    .pipe(lintScripts());
});

gulp.task('clean:tmp', function () {
  return del(['.tmp']);
});


gulp.task('client:build', ['lint:scripts', 'styles', 'copy:views'], function () {
  var jsFilter = $.filter('**/*.js', {restore: true});
  var cssFilter = $.filter('**/*.css', {restore: true});

  return gulp.src(paths.views.main)
    .pipe($.useref({searchPath: [mrmedia.app, '.tmp']}))
    .pipe(jsFilter)
    .pipe($.ngAnnotate())
    .pipe($.uglify())
    .pipe(jsFilter.restore)
    .pipe(cssFilter)
    .pipe($.cssnano({cache: true}))
    .pipe(cssFilter.restore)
    .pipe($.rev())
    .pipe($.revReplace())
    .pipe(gulp.dest(mrmedia.dist));
});

  // gulp.task('client:dev', ['styles', 'copy:views'], function () {
  gulp.task('client:dev', ['lint:scripts', 'styles', 'copy:views'], function () {
  // var jsFilter = $.filter('**/*.js', {restore: true});
  // var cssFilter = $.filter('**/*.css', {restore: true});

  return gulp.src(paths.views.main)
    .pipe($.useref({searchPath: [mrmedia.app, '.tmp']}))
    .pipe(gulp.dest(mrmedia.dist));
});

// inject bower components
gulp.task('bower', function () {
  return gulp.src(paths.views.main)
    .pipe(wiredep({
      directory: 'bower_components'
    }))
  .pipe(gulp.dest(mrmedia.app));
});

///////////
// Build //
///////////

gulp.task('clean:dist', function () {
  del(['dist']);
});

gulp.task('copy:views', function () {
  return gulp.src(mrmedia.app + '/views/**/*')
    .pipe(gulp.dest(mrmedia.dist + '/views'));
});

gulp.task('images', function () {
  return gulp.src(mrmedia.app + '/images/**/*')
    // .pipe($.cache($.imagemin({
    //     optimizationLevel: 5,
    //     progressive: true,
    //     interlaced: true
    // })))
    .pipe(gulp.dest(mrmedia.dist + '/images'));
});

gulp.task('copy:extras', function () {
  return gulp.src(mrmedia.app + '/*/.*', { dot: true })
    .pipe(gulp.dest(mrmedia.dist));
});

gulp.task('copy:fonts', function () {
    gulp.src(mrmedia.app + '/fonts/**/*')
        .pipe(gulp.dest(mrmedia.dist + '/fonts'));
    gulp.src('./bower_components/font-awesome/fonts/**/*.{ttf,woff,eof,svg}*')
        .pipe(gulp.dest('./dist/fonts'));
    gulp.src('./bower_components/bootstrap/fonts/**/*.{ttf,woff,eof,svg}*')
        .pipe(gulp.dest('./dist/fonts'));

});

gulp.task('build', ['clean:dist'], function () {
  runSequence(['images', 'copy:extras', 'copy:fonts', 'client:build']);
});

gulp.task('dev', ['clean:dist'], function () {
  runSequence(['images', 'copy:extras', 'copy:fonts', 'client:dev']);
});

gulp.task('default', ['build']);

// Watch
gulp.task('serve', ['browser-sync'], function () {
  $.watch(paths.styles)
        .pipe($.plumber())
        .pipe(styles());
  gulp.watch(paths.views.files, ['copy:views']);

  gulp.watch('{app/index.html,app/scripts/**/*.js,.tmp/styles/**/*.css}', ['client:dev']);
  gulp.watch('bower.json', ['bower']);
});

gulp.task('browser-sync', ['dev'], function () {
     var files = [
          'app/index.html',
          'app/views/*.html',
          'app/styles/**/*.css',
          'app/images/**/*.png',
          'app/scripts/**/*.js',
          'dist/**/*'
     ];

   browserSync.init(files, {
      server: {
         baseDir: "./dist"
      }
   });
   // Watch any files in dist/, reload on change
   gulp.watch(['dist/**/*']).on('change', browserSync.reload);
});
