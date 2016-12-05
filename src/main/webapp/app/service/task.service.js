'use strict';

woodstock.factory('TaskService', ['$resource', function ($resource) {
    return $resource('/api/modules/:moduleId/tasks/:taskId', {moduleId: '@_moduleId'}, {
        create: {
            method: 'POST'
        },
        update: {
            method: 'PUT'
        }
    });
}]);
