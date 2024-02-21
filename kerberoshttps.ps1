# Get host FQDN
$FQDN = (Get-WmiObject Win32_ComputerSystem).DNSHostName + "." + (Get-WmiObject Win32_ComputerSystem).Domain

# Generate New cert for the server to use for Kerberos
$cert = New-SelfSignedCertificate -DnsName $FQDN -CertStoreLocation "cert:\LocalMachine\My" -NotAfter (Get-Date).AddYears(10)

# Enable winrm 5986 with the new cert generated
$thumbprint = $cert.Thumbprint
New-Item -Path WSMan:\localhost\Listener -Transport HTTPS -Address * -CertificateThumbPrint $thumbprint -Hostname $FQDN

# Allow inbound traffic on port 5986 (WinRM HTTPS) in the Windows Firewall
New-NetFirewallRule -Name "WinRM HTTPS" -DisplayName "WinRM HTTPS" -Enabled True -Direction Inbound -Protocol TCP -Action Allow -LocalPort 5986 -ErrorAction SilentlyContinue
