"
" spring.vim -- functions and autocommands for working with Spring Boot
" projects.
"
" To create a project:
" 1. Create a directory.
" 2. Navigate to that directory in vim.
" 3. :call CreateSpringProject('com.timjstewart')
"
" To create resources:
" 1. :call CreateResource('Player', 'com.timjstewart')
" 2. Add fields to domain object.
" 3. Add tests to domain tests and controller tests.
"
augroup spring
    au!
    autocmd BufNewFile Application.java                  0r ~/spring/Application.java      | call InitializeJavaFile()
    autocmd BufNewFile SwaggerConfig.java                0r ~/spring/SwaggerConfig.java    | call InitializeJavaFile()
    autocmd BufNewFile */controller/*Controller.java     0r ~/spring/Controller.java       | call InitializeJavaFile()
    autocmd BufNewFile */repository/*Repository.java     0r ~/spring/Repository.java       | call InitializeJavaFile()
    autocmd BufNewFile */main/*/domain/*.java            0r ~/spring/Domain.java           | call InitializeJavaFile()
    autocmd BufNewFile */test/*/utility/*Page.java       0r ~/spring/Page.java             | call InitializeJavaFile()
    autocmd BufNewFile */controller/*ControllerTest.java 0r ~/spring/ControllerTest.java   | call InitializeJavaFile()
    autocmd BufNewFile */repository/*RepositoryTest.java 0r ~/spring/RepositoryTest.java   | call InitializeJavaFile()
    autocmd BufNewFile */presentation/*ViewModel.java    0r ~/spring/ViewModel.java        | call InitializeJavaFile()
    autocmd BufNewFile pom.xml                           0r ~/spring/Pom.xml               | silent write!
    autocmd BufNewFile application.properties            0r ~/spring/properties.properties | silent write!
augroup END

function! CreateResource(resource, rootPackage)
    call CreateRepository(a:resource, a:rootPackage)
    call CreateController(a:resource, a:rootPackage)
    call CreateViewModel(a:resource, a:rootPackage)
    call CreateDomain(a:resource, a:rootPackage)
endfunction

function! CreateViewModel(resource, rootPackage)
    call CreateJavaSourceFile(a:resource, 'main', a:rootPackage, 'presentation', a:resource . 'ViewModel.java')
endfunction

function! CreateRepository(resource, rootPackage)
    call CreateJavaSourceFile(a:resource, 'test', a:rootPackage, 'repository', a:resource . 'RepositoryTest.java')
    call CreateJavaSourceFile(a:resource, 'main', a:rootPackage, 'repository', a:resource . 'Repository.java')
endfunction

function! CreateService(resource, rootPackage)
    call CreateJavaSourceFile(a:resource, 'test', a:rootPackage, 'service', a:resource . 'ServiceTest.java')
    call CreateJavaSourceFile(a:resource, 'main', a:rootPackage, 'service', a:resource . 'Service.java')
endfunction

function! CreateDomain(resource, rootPackage)
    call CreateJavaSourceFile(a:resource, 'main', a:rootPackage, 'domain', a:resource . '.java')
endfunction

function! CreateController(resource, rootPackage)
    call CreateJavaSourceFile(a:resource, 'test', a:rootPackage, 'controller', a:resource . 'ControllerTest.java')
    call CreateJavaSourceFile(a:resource, 'test', a:rootPackage, 'utility', a:resource . 'Page.java')
    call CreateJavaSourceFile(a:resource, 'main', a:rootPackage, 'controller', a:resource . 'Controller.java')
endfunction

function! CreateJavaSourceFile(resource, kind, rootPackage, package, fileName)
    let l:packageDir = GetPackageDirectory(a:kind, a:rootPackage, a:package)
    let l:filePath = l:packageDir . '/' . a:fileName
    execute 'e ' . l:filePath
    execute '%s/Xxx/' . a:resource . '/ge'
    execute '%s/xxx/' . tolower(a:resource) . '/ge | write'
    return l:filePath
endfunction

function! GetPackageDirectory(kind, rootPackage, ...)
    let l:package = a:1
    let l:dir = GetJavaProjectDirectory() . '/src/' . a:kind . '/java/' . substitute(a:rootPackage, '\.', '/', 'g') 
    if a:0 > 0
        return l:dir . '/' . l:package
    else
        return l:dir
endfunction

function! CreateSpringProject(rootPackage)
    let l:rootDir = getcwd()

    call CreateJavaProject(a:rootPackage, l:rootDir)

    " Create Spring Boot configuration file
    let l:propsFile = l:rootDir . '/src/main/resources/application.properties'
    echom "Creating application properties file:" l:propsFile
    execute 'e ' . l:propsFile

    echom "Creating Application.java"
    execute 'e ' . GetPackageDirectory('main', a:rootPackage, '') . 'Application.java'

    echom "Creating SwaggerConfig.java"
    execute 'e ' . GetPackageDirectory('main', a:rootPackage, 'configuration') . '/SwaggerConfig.java'
endfunction

function! CreateJavaProject(rootPackage, rootDir)
    echo "Creating Java Project in" a:rootDir

    " Create Maven Project Object Model file.
    let l:pomFile = a:rootDir . '/pom.xml'
    echo "Creating pom.xml file:" l:pomFile
    execute 'e ' . l:pomFile

    let l:resDir = a:rootDir . '/src/main/resources'
    " echom "Creating directory:" l:resDir
    call mkdir(l:resDir, 'p')

    let l:noTestDirs = ['presentation', 'domain', 'configuration']

    for kind in ['main', 'test']
        for lang in ['java']
            for pkg in ['domain', 'repository', 'controller', 'service', 'eventing', 'utility', 'presentation', 'configuration']
                if kind != 'test' || index(l:noTestDirs, pkg) == -1
                    let l:dir = a:rootDir . '/src/' . kind . '/'. lang . '/' . substitute(a:rootPackage, '\.', '/', 'g') . '/' . pkg
                    " echom "Creating directory:" l:dir
                    call mkdir(l:dir, 'p')
                endif
            endfor
        endfor
    endfor
endfunction

function! InitializeJavaFile()
    " Change to the directory where the file was created so that computing the
    " package statement will work properly.
    " echom "Initializing Java File - filename:" expand("%") 'in directory:' expand("%:h") GetRootPackage() GetCurrentPackage()
    call append(0, GetPackageStatement())
    call append(1, "")
    execute '%s/PACK/' . GetRootPackage() . '/ge | write'
endfunction

function! GetPackageStatement()
    return "package " . GetCurrentPackage() . ";"
endfunction

function! GetCurrentPackage()
    let l:projdir = GetJavaProjectDirectory()
    " echom "PROJDIR:" l:projdir

    let l:filedir = expand("%:p:h")
    " echom "FILEDIR:" l:filedir

    " Chop off the project directory prefix of of the current working
    " directory.
    let l:fullpath = substitute(l:filedir, l:projdir, "", "")
    " echom "FULLPATH:" l:fullpath

    " Chop off the /src/main/java prefix
    let l:path = substitute(l:fullpath, 'src/\(main\|test\)/java', '', '')
    " echom "PATH2:" l:path

    " Chop off leading /
    let l:path = substitute(l:path, '^[/]*', '', '')
    " echom "PATH1:" l:path

    " Replace slashes with periods.
    let l:package = substitute(l:path, '/', '.', 'g')
    " echom "PACKAGE:" l:package

    return l:package
endfunction

function! GetRootPackage()
    let l:curpkg = GetCurrentPackage()
    let l:tokens = split(l:curpkg, '\.')
    return get(l:tokens, 0) . '.' . get(l:tokens, 1)
endfunction

function! GetPomFile(pwd)
    if a:pwd ==# "\/"
        return 0
    else
        let l:fn = a:pwd . "/pom.xml"

        if filereadable(expand(l:fn))
            return l:fn
        else
            let l:parent = fnamemodify(a:pwd, ':h')
            return GetPomFile(l:parent)
        endif
    endif
endfunction

function! GetJavaProjectDirectory()
    let pomFile = GetPomFile(getcwd())
    if strlen(l:pomFile) > 0
        return fnamemodify(l:pomFile, ':h')
    else
        return 0
    endif
endfunction

function! CreateJavaTagsFile()
    let projDir = GetJavaProjectDirectory()
    if strlen(l:projDir) > 0
        execute "!" . "ctags -R " . l:projDir
    endif
endfunction

function! MavenRunCommand(command)
    let pomFile = GetPomFile(getcwd())
    if strlen(l:pomFile) >= 0
        set makeprg='mvn'
        set errorformat=[ERROR]\ %f:[%l\\,%v]\ %m
        execute 'make -q -f ' . l:pomFile . ' ' . a:command
    else
        echom "Could not find pom file in " . getcwd()
    endif
endfunction

function! MavenClean()
    return MavenRunCommand('clean')
endfunction

function! MavenCompile()
    return MavenRunCommand('compile')
endfunction

function! MavenClean()
    return MavenRunCommand('clean')
endfunction

function! MavenTest()
    return MavenRunCommand('test')
endfunction

function! MavenPackage()
    return MavenRunCommand('package')
endfunction

function! MavenInstall()
    return MavenRunCommand('install')
endfunction

function! MavenSite()
    return MavenRunCommand('site')
endfunction

autocmd FileType java noremap <buffer> <leader>mc :call MavenCompile() <CR>
autocmd FileType java noremap <buffer> <leader>mp :call MavenPackage() <CR>
autocmd FileType java noremap <buffer> <leader>mt :call MavenTest() <CR>
autocmd FileType java noremap <buffer> <leader>mi :call MavenInstall() <CR>
autocmd FileType java noremap <buffer> <leader>ms :call MavenSite() <CR>
autocmd FileType java noremap <buffer> <leader>mk :call MavenClean() <CR>

