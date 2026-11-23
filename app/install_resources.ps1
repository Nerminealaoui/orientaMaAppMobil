$ErrorActionPreference = "Stop"
$utf8 = New-Object System.Text.UTF8Encoding($false)

function Write-Res($relPath, $content) {
    $full = Join-Path (Get-Location) $relPath
    New-Item -ItemType Directory -Force -Path (Split-Path $full) | Out-Null
    [System.IO.File]::WriteAllText($full, $content, $utf8)
    Write-Host "OK  $relPath"
}

$drawable = "app\src\main\res\drawable"
$header = '<?xml version="1.0" encoding="utf-8"?>'
$stroke = '    <stroke android:width="1dp" android:color="@color/om_border" />' + "`n"

function Shape($color, $strokeLine, $radius) {
@"
$header
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="$color" />
$strokeLine    <corners android:radius="$radius" />
</shape>
"@
}

Write-Res "$drawable\bg_input.xml"  (Shape "@color/white" $stroke "12dp")
Write-Res "$drawable\bg_button.xml" (Shape "@color/om_primary" "" "14dp")
Write-Res "$drawable\bg_card.xml"   (Shape "@color/om_card" $stroke "14dp")
Write-Res "$drawable\bg_logo.xml"   (Shape "@color/om_primary" "" "10dp")

function Icon($path) {
@"
$header
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000" android:pathData="$path" />
</vector>
"@
}

Write-Res "$drawable\ic_mail.xml"    (Icon "M20,4H4C2.9,4 2,4.9 2,6v12c0,1.1 0.9,2 2,2h16c1.1,0 2,-0.9 2,-2V6c0,-1.1 -0.9,-2 -2,-2zM20,8l-8,5 -8,-5V6l8,5 8,-5v2z")
Write-Res "$drawable\ic_lock.xml"    (Icon "M18,8h-1V6c0,-2.76 -2.24,-5 -5,-5S7,3.24 7,6v2H6c-1.1,0 -2,0.9 -2,2v10c0,1.1 0.9,2 2,2h12c1.1,0 2,-0.9 2,-2V10c0,-1.1 -0.9,-2 -2,-2zM12,17c-1.1,0 -2,-0.9 -2,-2s0.9,-2 2,-2 2,0.9 2,2 -0.9,2 -2,2zM9,8V6c0,-1.66 1.34,-3 3,-3s3,1.34 3,3v2H9z")
Write-Res "$drawable\ic_eye.xml"     (Icon "M12,4.5C7,4.5 2.73,7.61 1,12c1.73,4.39 6,7.5 11,7.5s9.27,-3.11 11,-7.5c-1.73,-4.39 -6,-7.5 -11,-7.5zM12,17c-2.76,0 -5,-2.24 -5,-5s2.24,-5 5,-5 5,2.24 5,5 -2.24,5 -5,5zM12,9c-1.66,0 -3,1.34 -3,3s1.34,3 3,3 3,-1.34 3,-3 -1.34,-3 -3,-3z")
Write-Res "$drawable\ic_arrow.xml"   (Icon "M12,4l-1.41,1.41L16.17,11H4v2h12.17l-5.58,5.59L12,20l8,-8z")
Write-Res "$drawable\ic_compass.xml" (Icon "M12,10.9c-0.61,0 -1.1,0.49 -1.1,1.1s0.49,1.1 1.1,1.1c0.61,0 1.1,-0.49 1.1,-1.1s-0.49,-1.1 -1.1,-1.1zM12,2C6.48,2 2,6.48 2,12s4.48,10 10,10 10,-4.48 10,-10S17.52,2 12,2zM14.19,14.19L6,18l3.81,-8.19L18,6l-3.81,8.19z")

$colorsPath = Join-Path (Get-Location) "app\src\main\res\values\colors.xml"
$newColors = @"
    <color name="om_bg">#F7F5F0</color>
    <color name="om_primary">#2563EB</color>
    <color name="om_text">#14213D</color>
    <color name="om_hint">#9AA3B2</color>
    <color name="om_subtitle">#5B6577</color>
    <color name="om_border">#E3E6EC</color>
    <color name="om_card">#EFEBE2</color>
    <color name="om_green">#3F8F5B</color>

"@

if (Test-Path $colorsPath) {
    $xml = [System.IO.File]::ReadAllText($colorsPath)
    if ($xml -notmatch 'name="om_bg"') {
        if ($xml -notmatch 'name="white"') {
            $newColors += '    <color name="white">#FFFFFFFF</color>' + "`n"
        }
        $xml = $xml -replace '</resources>', ($newColors + '</resources>')
        [System.IO.File]::WriteAllText($colorsPath, $xml, $utf8)
        Write-Host "OK  colors.xml mis a jour"
    } else {
        Write-Host "colors.xml contient deja les couleurs om_*"
    }
} else {
    $xml = "$header`n<resources>`n$newColors    <color name=`"white`">#FFFFFFFF</color>`n</resources>`n"
    Write-Res "app\src\main\res\values\colors.xml" $xml
}

Write-Host "`nTermine. Lance maintenant : .\gradlew.bat assembleDebug"