jQuery.validator.addMethod("lettersandspacesonly", function (value, element) {
    return this.optional(element) || /^[a-z][a-z" "]+[a-z]$|^[a-z]$/i.test(value);
}, "Somente letras e espaços (começar e terminar com letras)");


jQuery.validator.addMethod("lettersonly", function (value, element) {
    return this.optional(element) || /^[a-z]+$/i.test(value);
}, "Somente letras");


$("#form").validate({
    rules: {
        nome: {
            lettersonly: true
        },  
        sobrenome: {
            lettersandspacesonly: true
        }
    }
});