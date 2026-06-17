$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $PSScriptRoot
$src = Join-Path $root "src\main\java"
$out = Join-Path $root "out\main"
$lib = Join-Path $root "lib\sqlite-jdbc.jar"

if (-not (Test-Path -LiteralPath $lib)) {
    throw "Missing SQLite JDBC driver at lib\sqlite-jdbc.jar"
}

if (Test-Path -LiteralPath $out) {
    Remove-Item -LiteralPath $out -Recurse -Force
}
New-Item -ItemType Directory -Path $out | Out-Null

$sources = Get-ChildItem -LiteralPath $src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -cp $lib -d $out $sources
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

Write-Host "Build complete: $out"
