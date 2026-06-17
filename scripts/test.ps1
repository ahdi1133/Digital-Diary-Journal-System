$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $PSScriptRoot
& (Join-Path $PSScriptRoot "build.ps1")

$testSrc = Join-Path $root "src\test\java"
$testOut = Join-Path $root "out\test"

if (Test-Path -LiteralPath $testOut) {
    Remove-Item -LiteralPath $testOut -Recurse -Force
}
New-Item -ItemType Directory -Path $testOut | Out-Null

$mainOut = Join-Path $root "out\main"
$lib = Join-Path $root "lib\sqlite-jdbc.jar"
$compileClasspath = @($mainOut, $lib) -join ";"
$sources = Get-ChildItem -LiteralPath $testSrc -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -cp $compileClasspath -d $testOut $sources
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

$runClasspath = @($testOut, $mainOut, $lib) -join ";"
java -cp $runClasspath com.digitaldiary.TestRunner
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}
