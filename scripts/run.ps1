$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $PSScriptRoot
& (Join-Path $PSScriptRoot "build.ps1")

$classpath = @(
    (Join-Path $root "out\main"),
    (Join-Path $root "lib\sqlite-jdbc.jar")
) -join ";"

java -cp $classpath com.digitaldiary.Main
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}
