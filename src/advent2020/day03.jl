# day 3: 2d collisions

function n_collisions(arr::Vector{String}, x::Int, y::Int)
    r = 1
    c = 1
    rows = length(arr)
    cols = length(arr[1])
    n_coll = 0
    while r < rows
        r += y
        c += x
        if (c > cols)
            c = c % cols
        end
        if (r > rows)
            break
        end
        if (arr[r][c] == '#')
            n_coll += 1
        end
    end
    return n_coll
end

function part2(arr, slope_list)
    return reduce(*, map(x -> n_collisions(arr, x[1], x[2]), slope_list))
end

function day03()
    open("resources/day3.txt") do file
        arr = readlines(file)
        return (n_collisions(arr, 3, 1), part2(arr, [(1,1), (3,1), (5,1), (7,1), (1,2)]))
    end
end

println(day03())