'use strict';

woodstock.controller('TicketListController', ['$scope', '$location', '$routeParams', 'TicketService',
    function ($scope, $location, $routeParams, TicketService) {
        TicketService.query({taskId: $routeParams.taskId}, function (data) {
            $scope.tickets = data;
            $scope.taskId = $routeParams.taskId;
            $scope.categoryId = $routeParams.categoryId;
        })
    }
]);
