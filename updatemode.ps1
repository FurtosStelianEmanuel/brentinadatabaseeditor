Start-Process -FilePath java `
-ArgumentList '-jar brentinadatabaseeditor.jar update' `

$succesmessage='--updatemodestarted--'
write-host $succesmessage