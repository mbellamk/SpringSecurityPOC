<!DOCTYPE html>
<html ng-app="myApp">
    <head lang="en">
        <meta charset="utf-8">
        <title>Custom Plunker</title>  
        <link rel="stylesheet" type="text/css" href="css/ui-grid.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <script src="js/jquery.js"></script>
        <script src="http//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.2/angular.min.js"></script>
        <script type="text/javascript" src="js/ui-grid.min.js"></script>
        <script type="text/javascript" src="js/main.js"></script>
    </head>
    <body ng-controller="MyCtrl">
        <div class="gridStyle" ng-grid="gridOptions"></div>
    </body>
</html>