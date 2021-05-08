# day 1: list number sums

function n_number_sum(n::Int, sum::Int, vec::Vector)
    if n == 1
        found = nothing
        if (found = indexin(sum, vec)) .!= nothing
            return vec[found[1]]
        else
            return nothing
        end
    else
        for i in vec
            if sum > i
                result = nothing
                if (result = n_number_sum(n - 1, sum - i, vec)) .!= nothing
                    return result * i
                end
            end
        end
    end

    return nothing
end

function day01()
    open("resources/day1.txt") do file
        nums = map((x) -> parse(Int, x), readlines(file))
        return (n_number_sum(2, 2020, nums), n_number_sum(3, 2020, nums))
    end
end

println(day01())