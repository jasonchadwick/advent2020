# day 4: bus times

function find_wait_time(start_time::Int, bus_times::Int)
    if bus_times == 0
        return 10^10
    end
    if (start_time % bus_times == 0)
        return 0
    end
    return bus_times - (start_time % bus_times)
end

function find_first_bus(start_time::Int, buses::Vector{Int})
    wait_times = map(x -> find_wait_time(start_time, x), buses)
    min_idx = argmin(wait_times)
    return wait_times[min_idx] * buses[min_idx]
end

function find_bus_sync_time(buses::Vector{Int})
    bus_lcm = lcm(filter(x -> x != 0, buses))
    max_idx = argmax(buses)
    max_bus = buses[max_idx]
    times = buses
    n_rounds = 1
    while true
        t_longest = n_rounds * max_bus
        times = map(x -> x == 0 ? 0 : x * Int(round(n_rounds * max_bus / x)), buses)
        for i in 1:length(times)
            if times[i] != 0
                if t_longest - times[i] != max_idx - i
                    @goto continue_while
                end
            end
        end
        return t_longest - (max_idx - 1)
        @label continue_while
        n_rounds += 1
    end
end

function parse_buses(s)
    if s[1] == 'x'
        return 0
    end
    return parse(Int, s)
end

function day13()
    open("resources/day13.txt") do file
        arr = readlines(file)
        start_time = parse(Int, arr[1])
        buses = map(parse_buses, split(arr[2], ','))
        return (find_first_bus(start_time, buses), nothing)
    end
end

println(day13())