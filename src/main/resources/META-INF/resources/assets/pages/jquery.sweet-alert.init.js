
!function ($) {
    "use strict";

    var SweetAlert = function () {
    };

    SweetAlert.prototype.init = function () {

        // Success alerts
        $('#create-parametrages-topright-success').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: $('#create-parametrages-succes-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        });
        
        $('#create-categorieIntrant-topright-success').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: $('#create-categorieIntrant-succes-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        });

        $('#create-user-topright-success').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: $('#create-user-succes-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-agent-topright-success').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: $('#create-agent-succes-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-role-topright-success').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: $('#create-role-succes-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-service-topright-success').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: $('#create-service-succes-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-ampliation-topright-success').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: $('#create-ampliation-succes-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 
        
        $('#create-odm-topright-success').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: $('#create-odm-succes-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-entitePublique-topright-success').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: $('#create-entitePublique-succes-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        // error alerts
        $('#create-parametrages-topright-error').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: $("[id$='create-parametrages-error-text']").val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-categorieIntrant-topright-error').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: $("[id$='create-categorieIntrant-error-text']").val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-user-topright-error').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: $("[id$='create-user-error-text']").val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-service-topright-error').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: $("[id$='create-service-error-text']").val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-ampliation-topright-error').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: $("[id$='create-ampliation-error-text']").val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-agent-topright-error').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: $("[id$='create-agent-error-text']").val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 
        
        $('#create-odm-topright-error').click(function () {
            Swal.fire({
              position: 'top-end',
              icon: 'error',
              title: $('#create-odm-error-text').val(),
              showConfirmButton: false,
              timer: 5000
            });
          }); 

          $('#create-producateur-topright-error').click(function () {
            Swal.fire({
              position: 'top-end',
              icon: 'error',
              title: $('#create-producateur-error-text').val(),
              showConfirmButton: false,
              timer: 5000
            });
          }); 

        $('#create-role-topright-error').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: $('#create-role-error-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        $('#create-entitePublique-topright-error').click(function () {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: $('#create-entitePublique-error-text').val(),
            showConfirmButton: false,
            timer: 5000
          });
        }); 

        //custom html alert
        $('#custom-html-alert').click(function () {
            Swal.fire({
                title: '<strong>HTML <u>example</u></strong>',
                type: 'info',
                html:
                  'You can use <b>bold text</b>, ' +
                  '<a href="//github.com">links</a> ' +
                  'and other HTML tags',
                showCloseButton: true,
                showCancelButton: true,
                focusConfirm: false,
                confirmButtonText:
                  '<i class="fa fa-thumbs-up"></i> Great!',
                confirmButtonAriaLabel: 'Thumbs up, great!',
                cancelButtonText:
                  '<i class="fa fa-thumbs-down"></i>',
                cancelButtonAriaLabel: 'Thumbs down',
              })
        });

        //Custom width padding
        $('#custom-padding-width-alert').click(function () {
            Swal.fire({
                title: 'Custom width, padding, background.',
                width: 600,
                padding: 100,
                background: 'rgba(254,254,254,0.9) url(assets/images/pattern.png)',
            })
        });

        //Auto Close
        $('#sa-auto-close').click(function () {
            var timerInterval
            Swal.fire({
            title: 'Auto close alert!',
            html: 'I will close in <strong></strong> seconds.',
            timer: 2000,
            onBeforeOpen: function() {
                Swal.showLoading()
                timerInterval = setInterval(function() {
                Swal.getContent().querySelector('strong')
                    .textContent = Swal.getTimerLeft()
                }, 100)
            },
            onClose: function() {
                clearInterval(timerInterval)
            }
            }).then(function(result) {
            if (
                // Read more about handling dismissals
                result.dismiss === Swal.DismissReason.timer
            ) {
                console.log('I was closed by the timer')
            }
            })
        });

        //Ajax
        $('#ajax-alert').click(function () {
            Swal.fire({
                title: 'Submit your Github username',
                input: 'text',
                inputAttributes: {
                  autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Look up',
                showLoaderOnConfirm: true,
                preConfirm: function(login) {
                  return fetch('//api.github.com/users/+ login')
                    .then(function(response) {
                      if (!response.ok) {
                        throw new Error(response.statusText)
                      }
                      return response.json()
                    })
                    .catch(function(error) {
                      Swal.showValidationMessage(
                        "Request failed:" +error
                      )
                    })
                },
                allowOutsideClick: function() {return !Swal.isLoading()}
              }).then(function(result) {
                if (result.value) {
                  Swal.fire({
                    title: "result.value.login's avatar",
                    imageUrl: result.value.avatar_url
                  })
                }
              })
        });

        //chaining modal alert
        $('#chaining-alert').click(function () {
            Swal.mixin({
                input: 'text',
                confirmButtonText: 'Next &rarr;',
                showCancelButton: true,
                progressSteps: ['1', '2', '3']
              }).queue([
                {
                  title: 'Question 1',
                  text: 'Chaining swal2 modals is easy'
                },
                'Question 2',
                'Question 3'
              ]).then(function(result) {
                if (result.value) {
                  Swal.fire({
                    title: 'All done!',
                    html:
                      'Your answers: <pre><code>' +
                        JSON.stringify(result.value) +
                      '</code></pre>',
                    confirmButtonText: 'Lovely!'
                  })
                }
              })
        });

        //Danger
        $('#dynamic-alert').click(function () {
            var ipAPI = 'https://api.ipify.org?format=json'
            Swal.queue([{
                title: 'Your public IP',
                confirmButtonText: 'Show my public IP',
                text:
                  'Your public IP will be received ' +
                  'via AJAX request',
                showLoaderOnConfirm: true,
                preConfirm: function() {
                  return fetch(ipAPI)
                    .then(function(response) {return response.json()})
                    .then(function(data) { Swal.insertQueueStep(data.ip)})
                    .catch(function() {
                      Swal.insertQueueStep({
                        type: 'error',
                        title: 'Unable to get your public IP'
                      })
                    })
                }
              }])
        });

        $('#sa-mixin').click(function () {
          var Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            onOpen: function(toast) {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
          })
          
          Toast.fire({
            icon: 'success',
            title: 'Signed in successfully'
          })
        });
    },
        //init
        $.SweetAlert = new SweetAlert, $.SweetAlert.Constructor = SweetAlert
}(window.jQuery),

//initializing
    function ($) {
        "use strict";
        $.SweetAlert.init()
    }(window.jQuery);