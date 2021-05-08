# day 2: string parsing

function parse_pword(p::String)
    dash = findfirst(isequal('-'), p)
    space = findfirst(isequal(' '), p)

    n = parse(Int, p[1: dash-1])
    m = parse(Int, p[dash+1: space-1])
    char = p[space+1]
    pword = p[space+4: end]

    return (n, m, char, pword)
end

function check_pwords_1(pwords::Vector{String})
    n_valid = 0
    for p in pwords
        (n, m, char, pword) = parse_pword(p)
        n_chars = length(filter((x) -> x == char, pword))
        if n_chars >= n && n_chars <= m
            n_valid += 1
        end
    end
    return n_valid
end

function check_pwords_2(pwords::Vector{String})
    n_valid = 0
    for p in pwords
        (n, m, char, pword) = parse_pword(p)
        first_char = (pword[n] == char)
        second_char = (pword[m] == char)
        if (first_char && !second_char) || (!first_char && second_char)
            n_valid += 1
        end
    end
    return n_valid
end

function day02()
    open("resources/day2.txt") do file
        pwords = readlines(file)
        return (check_pwords_1(pwords), check_pwords_2(pwords))
    end
end

println(day02())