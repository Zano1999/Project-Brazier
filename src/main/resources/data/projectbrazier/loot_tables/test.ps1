Copy Code
$files = Get-ChildItem -path "C:\Users\lukas\minecraft_modding\1.16\ProjectBrazier\src\main\resources\data\projectbrazier\loot_tables\blocks\" -filter *.json
foreach ($file in $files)
{
    $content = Get-Content $file
    if ($content -match "<placeholder>")
    {
        $content = $content -replace "<placeholder>", $file.BaseName
        Set-Content $file $content
    }
}

PAUSE